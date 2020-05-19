'use strict';

var app = (function($) {

    var $body = $('body'),
        page = $body.data('page');

    function init() {
        if (page === 'catalogDB') {
            catalogDB.init();
        }
    }
    
    return {
        init: init
    }    

})(jQuery);

jQuery(document).ready(app.init);