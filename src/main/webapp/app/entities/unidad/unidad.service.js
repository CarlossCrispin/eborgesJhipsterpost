(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Unidad', Unidad);

    Unidad.$inject = ['$resource'];

    function Unidad ($resource) {
        var resourceUrl =  'api/unidads/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
