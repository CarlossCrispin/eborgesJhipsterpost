(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('Sinodal', Sinodal);

    Sinodal.$inject = ['$resource'];

    function Sinodal ($resource) {
        var resourceUrl =  'api/sinodals/:id';

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
