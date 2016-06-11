package de.hawLandshut.scrum.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import de.hawLandshut.scrum.model.Backlogitem;
import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.model.State;
import de.hawLandshut.scrum.model.Task;

@Named
@ViewScoped
public class BurndownController extends Controller implements Serializable{

	private static final long serialVersionUID = 1L;

	public LineChartModel getBurndownChart(Sprint sprint){
				
		int allPoints = 0;	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		LineChartSeries tmpSeries;
		HashMap<String, Integer> burndownData = new HashMap<String, Integer>();
		LineChartModel dateModel = new LineChartModel();
		
		for(Backlogitem item: sprint.getBacklogitems()){
			allPoints += Integer.parseInt(item.getEstimate());
			if(isTaskDone(item.getTasks())){
				String tmpDate = lastChangeDate(item.getTasks());
				if(burndownData.containsKey(tmpDate)){
					int tmpEstimate = burndownData.get(tmpDate);
					tmpEstimate += Integer.parseInt(item.getEstimate());
					burndownData.put(tmpDate, tmpEstimate);
				}
				else{
					burndownData.put(tmpDate, Integer.parseInt(item.getEstimate()));
				}
			}
				
		}
		
		tmpSeries = createTrendSeries(burndownData, allPoints, sprint.getStart(), sprint.getEnd());
		if(tmpSeries != null){
			dateModel.addSeries(tmpSeries);
		}
		tmpSeries = createIdealSeries(burndownData, allPoints, sprint.getStart(), sprint.getEnd());
		if(tmpSeries != null){
			dateModel.addSeries(tmpSeries);
		}

		dateModel.setTitle("Burndown");
		dateModel.getAxis(AxisType.Y).setLabel("Points");
		dateModel.setLegendPosition("ne");
		DateAxis axis = new DateAxis();
		axis.setTickAngle(-50);
		axis.setMin(dateFormat.format((sprint.getStart())));
		axis.setMax(dateFormat.format(sprint.getEnd()));
		axis.setTickInterval("1 day");
		axis.setTickFormat("%b %#d, %y");

		dateModel.getAxes().put(AxisType.X, axis);
		
		dateModel.setAnimate(true);
		
		return dateModel;
	}
	
	private boolean isTaskDone(List<Task> tasks){
		
		if(tasks.size() == 0){
			return false;
		}
		
		for(Task t : tasks){
						
			if(t.getStatus() != State.Done){
				return false;
			}
		}
		
		return true;
	}
	
	private String lastChangeDate(List<Task> tasks){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date tmpDate = tasks.get(0).getLastChange();
		
		for(int i=1; i < tasks.size();i++){
			if(tmpDate.before(tasks.get(i).getLastChange())){
				tmpDate = tasks.get(i).getLastChange();
			}
		}
		
		return dateFormat.format(tmpDate);
	}
	
	private LineChartSeries createTrendSeries(HashMap<String, Integer> burndown, int startPoints, Date startDate, Date endDate){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		LineChartSeries series = new LineChartSeries();
		series.setLabel("Trend");
		
		series.set(dateFormat.format(startDate), startPoints);
		endDate = new Date(endDate.getTime() + (1000 * 60 * 60 * 24));
		
		for(Date date = startDate; date.before(endDate) && date.before(today); date = new Date(date.getTime() + (1000 * 60 * 60 * 24))){
	            if(burndown.containsKey(dateFormat.format(date))){
	            	startPoints -= burndown.get(dateFormat.format(date));
	            	series.set(dateFormat.format(date), startPoints);
	            }
	            else{
	            	series.set(dateFormat.format(date), startPoints);
	            }
	            	
			}
		return series;
	}
	
	private LineChartSeries createIdealSeries(HashMap<String, Integer> burndown, int startPoints, Date startDate, Date endDate){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		LineChartSeries series = new LineChartSeries();
		series.setLabel("Ideal Burndown");
		
		series.set(dateFormat.format(startDate), startPoints);
		series.set(dateFormat.format(endDate), 0);
		
		return series;
	}

}
