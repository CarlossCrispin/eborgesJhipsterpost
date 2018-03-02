(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Especialidad', Especialidad);

    Especialidad.$inject = ['$resource'];

    function Especialidad ($resource) {
        var resourceUrl =  'api/especialidads/:id';

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
