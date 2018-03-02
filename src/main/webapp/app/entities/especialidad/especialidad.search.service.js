(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('EspecialidadSearch', EspecialidadSearch);

    EspecialidadSearch.$inject = ['$resource'];

    function EspecialidadSearch($resource) {
        var resourceUrl =  'api/_search/especialidads/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
