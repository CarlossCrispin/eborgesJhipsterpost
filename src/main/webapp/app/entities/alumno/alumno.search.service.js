(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('AlumnoSearch', AlumnoSearch);

    AlumnoSearch.$inject = ['$resource'];

    function AlumnoSearch($resource) {
        var resourceUrl =  'api/_search/alumnos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
