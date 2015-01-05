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
    $('#editor').each(function (data) {
        var editor = new Editor();
        editor.render();
    });

    $('.markdown').each(function () {
        var me = $(this);
        me.html(marked(me.text()));
    });

    $('img').each(function () {
        var me = $(this);
        me.addClass('img-responsive');
    });
});