(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('grado', {
            parent: 'entity',
            url: '/grado',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.grado.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grado/grados.html',
                    controller: 'GradoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('grado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('grado-detail', {
            parent: 'grado',
            url: '/grado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.grado.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grado/grado-detail.html',
                    controller: 'GradoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('grado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Grado', function($stateParams, Grado) {
                    return Grado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'grado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('grado-detail.edit', {
            parent: 'grado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grado/grado-dialog.html',
                    controller: 'GradoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Grado', function(Grado) {
                            return Grado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grado.new', {
            parent: 'grado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grado/grado-dialog.html',
                    controller: 'GradoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ngrado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('grado', null, { reload: 'grado' });
                }, function() {
                    $state.go('grado');
                });
            }]
        })
        .state('grado.edit', {
            parent: 'grado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grado/grado-dialog.html',
                    controller: 'GradoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Grado', function(Grado) {
                            return Grado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grado', null, { reload: 'grado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grado.delete', {
            parent: 'grado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grado/grado-delete-dialog.html',
                    controller: 'GradoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Grado', function(Grado) {
                            return Grado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grado', null, { reload: 'grado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
