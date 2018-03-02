(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('TipoAsesorSearch', TipoAsesorSearch);

    TipoAsesorSearch.$inject = ['$resource'];

    function TipoAsesorSearch($resource) {
        var resourceUrl =  'api/_search/tipo-asesors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
