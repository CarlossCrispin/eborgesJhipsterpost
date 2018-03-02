(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('GeneroSearch', GeneroSearch);

    GeneroSearch.$inject = ['$resource'];

    function GeneroSearch($resource) {
        var resourceUrl =  'api/_search/generos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
