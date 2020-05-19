var catalogDB = (function ($) {

    var ui = {
        $form: $('#filters-form'),
        $prices: $('#prices'),
        $limit: $('#records'),
        $pag: $('#pagination'),
        $pricesLabel: $('#prices-label'),
        $minPrice: $('#min-price'),
        $maxPrice: $('#max-price'),
        $categoryBtn: $('.js-category'),
        $manufacturers: $('#manufacturers'),
        $sort: $('#sort'),
        $goods: $('#goods'),
        $loadMore: $('#loadMore'),
    };
    var selectedCategory = 0;

    function init() {
        _initPrices({
            minPrice: 200,
            maxPrice: 70000
        });
        _bindHandlers();
        _getData({needsData: 'manufacturers,prices'});
    }


    function _bindHandlers() {
        ui.$categoryBtn.on('click', _changeCategory);
        ui.$manufacturers.on('change', 'input', _getData);
        ui.$sort.on('change', _getData);
        ui.$limit.on('change', _changeLimit);
        ui.$pag.on('click', _changePage);
    }

    function _resetFilters() {
        ui.$manufacturers.find('input').removeAttr('checked');
        ui.$minPrice.val(200);
        ui.$maxPrice.val(70000);
    }

    function _changeCategory() {
        var $this = $(this);
        ui.$categoryBtn.removeClass('active');
        $this.addClass('active');
        selectedCategory = $this.attr('data-category');
        _resetFilters();
        _getData({needsData: 'manufacturers,prices'});
    }

    function _onSlidePrices(event, elem) {
        _updatePricesUI({
            minPrice: elem.values[0],
            maxPrice: elem.values[1]
        });
    }

    function _updatePricesUI(options) {
        ui.$pricesLabel.html(options.minPrice + ' - ' + options.maxPrice + ' $');
        ui.$minPrice.val(options.minPrice);
        ui.$maxPrice.val(options.maxPrice);
    }

    function _initPrices(options) {
        ui.$prices.slider({
            range: true,
            min: options.minPrice,
            max: options.maxPrice,
            values: [options.minPrice, options.maxPrice],
            slide: _onSlidePrices,
            change: _getData
        });
        _updatePricesUI(options);
    }

    function _updatePrices(options) {
        ui.$prices.slider({
            change: null
        }).slider({
            min: options.minPrice,
            max: options.maxPrice,
            values: [options.minPrice, options.maxPrice]
        }).slider({
            change: _getData
        });
        _updatePricesUI(options);
    }

    function _changeLimit() {
        _getData({
            resetPage: true
        });
    }

    function _changePage(e) {
        e.preventDefault();
        e.stopPropagation();

        var $page = $(e.target).closest('li');
        ui.$pag.find('li').removeClass('active');
        $page.addClass('active');

        _getData();
    }

    function _getData(options) {
        var page = +ui.$pag.find('li.active').attr('data-page') || 1;
        var catalogData = 'page=' + page + '&' + 'category=' + selectedCategory + '&' + ui.$form.serialize();
        if (options && options.needsData) {
            catalogData += '&needs_data=' + options.needsData;
        }
        $.ajax({
            url: 'http://localhost:8080/catalog',
            data: catalogData,
            type: 'post',
            cache: false,
            error: function (xhr, status, error) {
                var errorMessage = xhr.status + ': ' + xhr.statusText
                alert('Error - ' + errorMessage);
            },
            success: function (responseText) {
                    $('.catalogList').append(responseText);
            }
        });
    }
    return {
        init: init
    }

})(jQuery);