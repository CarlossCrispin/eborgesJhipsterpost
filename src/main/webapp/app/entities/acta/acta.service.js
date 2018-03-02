(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Acta', Acta);

    Acta.$inject = ['$resource', 'DateUtils'];

    function Acta ($resource, DateUtils) {
        var resourceUrl =  'api/actas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechatomagrado = DateUtils.convertDateTimeFromServer(data.fechatomagrado);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
