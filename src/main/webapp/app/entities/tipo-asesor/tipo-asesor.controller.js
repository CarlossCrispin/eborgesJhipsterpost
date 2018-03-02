(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TipoAsesorController', TipoAsesorController);

    TipoAsesorController.$inject = ['TipoAsesor', 'TipoAsesorSearch'];

    function TipoAsesorController(TipoAsesor, TipoAsesorSearch) {

        var vm = this;

        vm.tipoAsesors = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TipoAsesor.query(function(result) {
                vm.tipoAsesors = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TipoAsesorSearch.query({query: vm.searchQuery}, function(result) {
                vm.tipoAsesors = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
