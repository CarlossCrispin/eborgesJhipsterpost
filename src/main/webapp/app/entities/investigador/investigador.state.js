(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('investigador', {
            parent: 'entity',
            url: '/investigador',
            data: {
                authorities: ['ROLE_USER','ROLE_INVESTIGADOR'],
                pageTitle: 'eBorgesApp.investigador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/investigador/investigadors.html',
                    controller: 'InvestigadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('investigador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('investigador-detail', {
            parent: 'investigador',
            url: '/investigador/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_INVESTIGADOR'],
                pageTitle: 'eBorgesApp.investigador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/investigador/investigador-detail.html',
                    controller: 'InvestigadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('investigador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Investigador', function($stateParams, Investigador) {
                    return Investigador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'investigador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('investigador-detail.edit', {
            parent: 'investigador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_INVESTIGADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigador/investigador-dialog.html',
                    controller: 'InvestigadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Investigador', function(Investigador) {
                            return Investigador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('investigador.new', {
            parent: 'investigador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_INVESTIGADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigador/investigador-dialog.html',
                    controller: 'InvestigadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido1: null,
                                apellido2: null,
                                esexterno: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('investigador', null, { reload: 'investigador' });
                }, function() {
                    $state.go('investigador');
                });
            }]
        })
        .state('investigador.edit', {
            parent: 'investigador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_INVESTIGADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigador/investigador-dialog.html',
                    controller: 'InvestigadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Investigador', function(Investigador) {
                            return Investigador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('investigador', null, { reload: 'investigador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('investigador.delete', {
            parent: 'investigador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_INVESTIGADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigador/investigador-delete-dialog.html',
                    controller: 'InvestigadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Investigador', function(Investigador) {
                            return Investigador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('investigador', null, { reload: 'investigador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
