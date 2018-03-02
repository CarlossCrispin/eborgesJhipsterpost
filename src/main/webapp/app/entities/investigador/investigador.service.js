(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Investigador', Investigador);

    Investigador.$inject = ['$resource'];

    function Investigador ($resource) {
        var resourceUrl =  'api/investigadors/:id';

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
