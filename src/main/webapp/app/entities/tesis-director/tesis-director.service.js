(function() {
    'use strict';
    angular
        .module('eBorgesApp')
        .factory('TesisDirector', TesisDirector);

    TesisDirector.$inject = ['$resource'];

    function TesisDirector ($resource) {
        var resourceUrl =  'api/tesis-directors/:id';

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
