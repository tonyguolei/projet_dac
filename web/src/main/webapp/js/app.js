$(document).ready(function () {
    // CodeMirror Extension
    var enforceMaxLength = function (cm, change) {
        var maxLength = cm.getOption("maxLength");
        if (maxLength && change.update) {
            var str = change.text.join("\n");
            var delta = str.length - (cm.indexFromPos(change.to) - cm.indexFromPos(change.from));
            if (delta <= 0) {
                return true;
            }
            delta = cm.getValue().length + delta - maxLength;
            if (delta > 0) {
                str = str.substr(0, str.length - delta);
                change.update(change.from, change.to, str.split("\n"));
            }
        }
        return true;
    };

    var notifs = $('#notifications');
    console.log(notifs.find(('ul')));
    if (notifs.find('ul').hasClass('no-notifs')) {
        var label = notifs.find('.badge');
        label.removeClass('back-success');
        label.text(' ');
    }

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
        var me = $(this);
        var width = me.attr('data-width'); // null -> dont change
        var height = me.attr('data-height');
        var maxLen = me.attr('data-maxlen');
        var editor = new Editor();
        editor.render();
        editor.codemirror.setSize(width, height);
        if (maxLen) {
            editor.codemirror.setOption("maxLength", maxLen);
            editor.codemirror.on("beforeChange", enforceMaxLength);
        }
        window.editor = editor;
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