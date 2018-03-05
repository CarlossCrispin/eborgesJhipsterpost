(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisController', TesisController);

    TesisController.$inject = ['Tesis', 'TesisSearch'];

    function TesisController(Tesis, TesisSearch) {

        var vm = this;

        vm.teses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            
            Tesis.query(function(result) {
                vm.teses = result;
                vm.searchQuery = hibridos;
            });
            // TesisSearch.query({query: 'qui es igual al id del usuario '}, function(result) {
            //     vm.teses = result;
            //     vm.currentSearch = vm.searchQuery;
            // });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TesisSearch.query({query: vm.searchQuery}, function(result) {
                vm.teses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
