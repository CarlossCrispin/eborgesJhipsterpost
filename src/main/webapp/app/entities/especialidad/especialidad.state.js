(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('especialidad', {
            parent: 'entity',
            url: '/especialidad',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.especialidad.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/especialidad/especialidads.html',
                    controller: 'EspecialidadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('especialidad');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('especialidad-detail', {
            parent: 'especialidad',
            url: '/especialidad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.especialidad.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/especialidad/especialidad-detail.html',
                    controller: 'EspecialidadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('especialidad');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Especialidad', function($stateParams, Especialidad) {
                    return Especialidad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'especialidad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('especialidad-detail.edit', {
            parent: 'especialidad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidad/especialidad-dialog.html',
                    controller: 'EspecialidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Especialidad', function(Especialidad) {
                            return Especialidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('especialidad.new', {
            parent: 'especialidad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidad/especialidad-dialog.html',
                    controller: 'EspecialidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nespecialidad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('especialidad', null, { reload: 'especialidad' });
                }, function() {
                    $state.go('especialidad');
                });
            }]
        })
        .state('especialidad.edit', {
            parent: 'especialidad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidad/especialidad-dialog.html',
                    controller: 'EspecialidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Especialidad', function(Especialidad) {
                            return Especialidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('especialidad', null, { reload: 'especialidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('especialidad.delete', {
            parent: 'especialidad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidad/especialidad-delete-dialog.html',
                    controller: 'EspecialidadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Especialidad', function(Especialidad) {
                            return Especialidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('especialidad', null, { reload: 'especialidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
