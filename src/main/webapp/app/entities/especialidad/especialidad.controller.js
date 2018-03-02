(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('EspecialidadController', EspecialidadController);

    EspecialidadController.$inject = ['Especialidad', 'EspecialidadSearch'];

    function EspecialidadController(Especialidad, EspecialidadSearch) {

        var vm = this;

        vm.especialidads = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Especialidad.query(function(result) {
                vm.especialidads = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EspecialidadSearch.query({query: vm.searchQuery}, function(result) {
                vm.especialidads = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
