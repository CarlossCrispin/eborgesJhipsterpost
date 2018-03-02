(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('DepartamentoSearch', DepartamentoSearch);

    DepartamentoSearch.$inject = ['$resource'];

    function DepartamentoSearch($resource) {
        var resourceUrl =  'api/_search/departamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
