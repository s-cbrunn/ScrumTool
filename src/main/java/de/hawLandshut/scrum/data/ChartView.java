package de.hawLandshut.scrum.data;

import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import de.hawLandshut.scrum.model.Sprint;

@ManagedBean
public class ChartView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineChartModel animatedModel1;
	private BarChartModel animatedModel2;
	private LineChartModel dateModel;

	@PostConstruct
	public void init() {
		createAnimatedModels();
	}

	public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}

	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}

	private void createAnimatedModels() {
		animatedModel1 = initLinearModel();
		animatedModel1.setTitle("Burndown");
		animatedModel1.setAnimate(true);
		animatedModel1.setLegendPosition("se");

		DateAxis axis = new DateAxis("Dates");
		axis.setTickAngle(-50);
		axis.setMax("2014-02-01");
		axis.setTickFormat("%b %#d, %y");

		Axis yAxis = animatedModel1.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(100);

		animatedModel2 = initBarModel();
		animatedModel2.setTitle("Bar Charts");
		animatedModel2.setAnimate(true);
		animatedModel2.setLegendPosition("ne");
		yAxis = animatedModel2.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(200);

	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Boys");
		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 135);
		girls.set("2008", 120);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		series1.set("2014-01-01 00:10:50", 51);
		series1.set("2014-01-02 00:10:51", 22);
		series1.set("2014-01-05 00:10:52", 65);
		series1.set("2014-01-04 00:10:53", 35);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");

		series1.set("2014-02-01 00:10:50", 51);
		series1.set("2014-01-03 00:10:51", 22);
		series1.set("2014-01-04 00:10:52", 65);
		series1.set("2014-01-05 00:10:53", 35);

		model.addSeries(series1);
		model.addSeries(series2);

		return model;
	}
	
	public LineChartModel getDateModel(Sprint sprint) {
		Date d = new Date();
		System.out.println(d.toString());
		createDateModel();
        return dateModel;
    }

	private void createDateModel() {
		dateModel = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		series1.set("2014-01-01", 100);
		series1.set("2014-01-06", 70);
		series1.set("2014-01-12", 65);
		series1.set("2014-01-18", 60);
		series1.set("2014-01-24", 60);
		series1.set("2014-01-24", 60);
		series1.set("2014-01-24", 10);
		series1.set("2014-01-30", 0);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");

		series2.set("2014-01-01", 100);
	//	series2.set("2014-01-06", 73);
	//	series2.set("2014-01-12", 24);
	//	series2.set("2014-01-18", 12);
	//	series2.set("2014-01-24", 74);
		series2.set("2014-02-0", 0);

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);

		dateModel.setTitle("Burndown");
		DateAxis axis = new DateAxis();
		axis.setTickAngle(-50);
		axis.setMax("2014-02-01");
		axis.setTickFormat("%b %#d, %y");

		dateModel.getAxes().put(AxisType.X, axis);
		dateModel.setAnimate(true);
	}

}
