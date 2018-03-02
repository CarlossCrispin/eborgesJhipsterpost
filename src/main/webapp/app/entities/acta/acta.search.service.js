(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('ActaSearch', ActaSearch);

    ActaSearch.$inject = ['$resource'];

    function ActaSearch($resource) {
        var resourceUrl =  'api/_search/actas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
