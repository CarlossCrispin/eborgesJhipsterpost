(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('GradoSearch', GradoSearch);

    GradoSearch.$inject = ['$resource'];

    function GradoSearch($resource) {
        var resourceUrl =  'api/_search/grados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
