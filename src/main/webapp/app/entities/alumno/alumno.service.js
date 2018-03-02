(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Alumno', Alumno);

    Alumno.$inject = ['$resource'];

    function Alumno ($resource) {
        var resourceUrl =  'api/alumnos/:id';

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
