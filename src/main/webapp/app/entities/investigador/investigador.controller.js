(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('InvestigadorController', InvestigadorController);

    InvestigadorController.$inject = ['Investigador', 'InvestigadorSearch'];

    function InvestigadorController(Investigador, InvestigadorSearch) {

        var vm = this;

        vm.investigadors = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Investigador.query(function(result) {
                vm.investigadors = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            InvestigadorSearch.query({query: vm.searchQuery}, function(result) {
                vm.investigadors = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
