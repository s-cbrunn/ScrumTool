
function handleMessage(facesmessage) {
    facesmessage.severity = 'info';
    PF('growl').show([facesmessage]);
}
