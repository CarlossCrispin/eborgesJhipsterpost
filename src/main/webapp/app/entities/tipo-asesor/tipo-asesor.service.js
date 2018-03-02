(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('TipoAsesor', TipoAsesor);

    TipoAsesor.$inject = ['$resource'];

    function TipoAsesor ($resource) {
        var resourceUrl =  'api/tipo-asesors/:id';

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
