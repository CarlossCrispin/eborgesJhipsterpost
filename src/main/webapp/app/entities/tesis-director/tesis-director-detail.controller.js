(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDirectorDetailController', TesisDirectorDetailController);

    TesisDirectorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TesisDirector', 'Tesis', 'Investigador', 'TipoAsesor'];

    function TesisDirectorDetailController($scope, $rootScope, $stateParams, previousState, entity, TesisDirector, Tesis, Investigador, TipoAsesor) {
        var vm = this;

        vm.tesisDirector = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:tesisDirectorUpdate', function(event, result) {
            vm.tesisDirector = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
