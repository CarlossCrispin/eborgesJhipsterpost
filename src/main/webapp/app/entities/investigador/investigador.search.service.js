(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .factory('InvestigadorSearch', InvestigadorSearch);

    InvestigadorSearch.$inject = ['$resource'];

    function InvestigadorSearch($resource) {
        var resourceUrl =  'api/_search/investigadors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
