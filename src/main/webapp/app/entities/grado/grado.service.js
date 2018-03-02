(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Grado', Grado);

    Grado.$inject = ['$resource'];

    function Grado ($resource) {
        var resourceUrl =  'api/grados/:id';

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
