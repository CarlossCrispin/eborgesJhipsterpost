(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('DepartamentoController', DepartamentoController);

    DepartamentoController.$inject = ['Departamento', 'DepartamentoSearch'];

    function DepartamentoController(Departamento, DepartamentoSearch) {

        var vm = this;

        vm.departamentos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Departamento.query(function(result) {
                vm.departamentos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DepartamentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.departamentos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
