(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('SinodalSearch', SinodalSearch);

    SinodalSearch.$inject = ['$resource'];

    function SinodalSearch($resource) {
        var resourceUrl =  'api/_search/sinodals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
