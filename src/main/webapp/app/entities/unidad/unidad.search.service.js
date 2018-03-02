(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('UnidadSearch', UnidadSearch);

    UnidadSearch.$inject = ['$resource'];

    function UnidadSearch($resource) {
        var resourceUrl =  'api/_search/unidads/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
