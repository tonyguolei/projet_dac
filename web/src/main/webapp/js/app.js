$(document).ready(function () {
    console.log('Go on!');
    $('.datepicker').datepicker({
        format: 'YYYY-MM-DD'
    });
    $('.time-relative').each(function () {
        console.log('hey');
        var me = $(this);
        me.text(moment(me.text(), "YYYY-MM-DD").fromNow());
    });
});