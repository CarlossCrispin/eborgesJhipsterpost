(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TipoAsesorDetailController', TipoAsesorDetailController);

    TipoAsesorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoAsesor'];

    function TipoAsesorDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoAsesor) {
        var vm = this;

        vm.tipoAsesor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:tipoAsesorUpdate', function(event, result) {
            vm.tipoAsesor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
