(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Tesis', Tesis);

    Tesis.$inject = ['$resource', 'DateUtils'];

    function Tesis ($resource, DateUtils) {
        var resourceUrl =  'api/teses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechadepublicacion = DateUtils.convertDateTimeFromServer(data.fechadepublicacion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
