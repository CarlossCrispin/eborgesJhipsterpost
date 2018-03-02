(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('AlumnoController', AlumnoController);

    AlumnoController.$inject = ['Alumno', 'AlumnoSearch'];

    function AlumnoController(Alumno, AlumnoSearch) {

        var vm = this;

        vm.alumnos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Alumno.query(function(result) {
                vm.alumnos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AlumnoSearch.query({query: vm.searchQuery}, function(result) {
                vm.alumnos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
