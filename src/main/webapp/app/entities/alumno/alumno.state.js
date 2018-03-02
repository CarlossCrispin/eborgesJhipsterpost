(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alumno', {
            parent: 'entity',
            url: '/alumno',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.alumno.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alumno/alumnos.html',
                    controller: 'AlumnoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alumno');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('alumno-detail', {
            parent: 'alumno',
            url: '/alumno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.alumno.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alumno/alumno-detail.html',
                    controller: 'AlumnoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alumno');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Alumno', function($stateParams, Alumno) {
                    return Alumno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alumno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alumno-detail.edit', {
            parent: 'alumno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alumno/alumno-dialog.html',
                    controller: 'AlumnoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alumno', function(Alumno) {
                            return Alumno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alumno.new', {
            parent: 'alumno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alumno/alumno-dialog.html',
                    controller: 'AlumnoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido1: null,
                                apellido2: null,
                                matricula: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alumno', null, { reload: 'alumno' });
                }, function() {
                    $state.go('alumno');
                });
            }]
        })
        .state('alumno.edit', {
            parent: 'alumno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alumno/alumno-dialog.html',
                    controller: 'AlumnoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alumno', function(Alumno) {
                            return Alumno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alumno', null, { reload: 'alumno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alumno.delete', {
            parent: 'alumno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alumno/alumno-delete-dialog.html',
                    controller: 'AlumnoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Alumno', function(Alumno) {
                            return Alumno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alumno', null, { reload: 'alumno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
