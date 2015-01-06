$(document).ready(function () {
    $('.datepicker').datepicker({
        format: 'YYYY-MM-DD'
    });
    $('.time-relative').each(function () {
        var me = $(this);
        var date = me.text();
        var format = me.attr('data-format') || 'YYYY-MM-DD';
        console.log(format);
        me.text(moment(date, format).fromNow())
          .tooltip({
              delay: 300,
              title: date
        });
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