(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('TesisDirectorSearch', TesisDirectorSearch);

    TesisDirectorSearch.$inject = ['$resource'];

    function TesisDirectorSearch($resource) {
        var resourceUrl =  'api/_search/tesis-directors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
