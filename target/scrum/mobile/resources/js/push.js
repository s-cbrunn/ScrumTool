
function handleMessage(facesmessage) {
    facesmessage.severity = 'info';
    PF('growl').show([facesmessage]);
}

PrimeFaces.Mobile.navigate('#second', {
    reverse: true|false,
    transition: 'fade'
});
