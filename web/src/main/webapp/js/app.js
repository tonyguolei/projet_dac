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

    var notifs = $('#notifications'),
      pms = $('#privateMessages');
    if (notifs.find('ul').hasClass('no-notifs')) {
        var label = notifs.find('.badge');
        label.removeClass('back-success');
        label.text(' ');
    }
    if (pms.find('ul').hasClass('no-notifs')) {
        var label = pms.find('.badge');
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
        //console.log(format);
        me.text(moment(date, format).fromNow())
                .tooltip({
                    delay: 300,
                    title: date
                });
    });

    $('.tooltip-need').tooltip();

    $('.editor').each(function (data) {
        var me = $(this);
        var width = me.attr('data-width'); // null -> dont change
        var height = me.attr('data-height');
        var maxLen = me.attr('data-maxlen');
        var editor = new Editor({
            element: this
        });
        //editor.render();
        editor.codemirror.setSize(width, height);
        if (maxLen) {
            editor.codemirror.setOption("maxLength", maxLen);
            editor.codemirror.on("beforeChange", enforceMaxLength);
        }
        window.editor = editor;
    });

    // render markdown
    $('.markdown').each(function () {
        var me = $(this);
        me.html(marked(me.text()));
    });

    // embed videos
    $('.enable-video').each(function () {
        var me = $(this);
        var ytReg = new RegExp(".*youtube\.com.*\?v=([^&]*)");
        me.find('a').each(function () {
            var a = ytReg.exec(this.href);
            if (a) {
                this.innerHTML = '<div class="video-container"><iframe max-width="560" width="100%" height="315" src="//www.youtube.com/embed/' + a[1] + '" frameborder="0" allowfullscreen></iframe></div>';
            }
        });
    });

    //  make progress bars fancier!
    $('.fund-level').each(function () {
        var me = $(this);
        var goal = me.attr('aria-valuemax');
        var value = me.attr('data-value');
        var perc = Math.min(1, value / goal) * 100 + "%";
        console.log('go to ' + perc);
        me.animate({
            width: perc
        });
    });

    $('.number').each(function () {
        console.log(this.innerHTML);
        this.innerHTML = new Number(this.innerHTML);
        console.log(this.innerHTML);
    });

    $('.project-tags').each(function () {
        var tags = this.innerHTML.split(',');
        this.innerHTML = '<span class="glyphicon glyphicon-tags"></span>';
        for (var i = 0; i < tags.length; i++) {
            if (tags[i].length > 0) {
                console.log('tag:' + tags[i] + '.');
                this.innerHTML += '<span class="label label-default"><a href="ControllerProject?action=search&tag=' + tags[i] + '">' + tags[i] + '</a></span>';
            }
        }
    });

    // make every image responsive
    $('img').each(function () {
        var me = $(this);
        me.addClass('img-responsive');
    });
});