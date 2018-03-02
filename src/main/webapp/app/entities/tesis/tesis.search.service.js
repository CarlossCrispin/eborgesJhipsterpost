(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('TesisSearch', TesisSearch);

    TesisSearch.$inject = ['$resource'];

    function TesisSearch($resource) {
        var resourceUrl =  'api/_search/teses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
