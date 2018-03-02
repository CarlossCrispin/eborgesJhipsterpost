(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('SinodalDetailController', SinodalDetailController);

    SinodalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sinodal', 'Acta', 'TipoAsesor', 'Investigador'];

    function SinodalDetailController($scope, $rootScope, $stateParams, previousState, entity, Sinodal, Acta, TipoAsesor, Investigador) {
        var vm = this;

        vm.sinodal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:sinodalUpdate', function(event, result) {
            vm.sinodal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
