package com.dev.properti.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dev.properti.ui.view.DestinasiHomeApp
import com.dev.properti.ui.view.HomeApp
import com.dev.properti.ui.view.jenisproperti.DestinasiDetailJenis
import com.dev.properti.ui.view.jenisproperti.DestinasiHomeJenis
import com.dev.properti.ui.view.jenisproperti.DestinasiInsertJenis
import com.dev.properti.ui.view.jenisproperti.DestinasiUpdateJenis
import com.dev.properti.ui.view.jenisproperti.DetailJenisView
import com.dev.properti.ui.view.jenisproperti.HomeJenisView
import com.dev.properti.ui.view.jenisproperti.InsertJenisView
import com.dev.properti.ui.view.jenisproperti.UpdateJenisView
import com.dev.properti.ui.view.manajerproperti.DestinasiDetailManajer
import com.dev.properti.ui.view.manajerproperti.DestinasiDetailManajer.IDMJR
import com.dev.properti.ui.view.manajerproperti.DestinasiHomeManajer
import com.dev.properti.ui.view.manajerproperti.DestinasiInsertManajer
import com.dev.properti.ui.view.manajerproperti.DestinasiUpdateManajer
import com.dev.properti.ui.view.manajerproperti.DetailManajerView
import com.dev.properti.ui.view.manajerproperti.HomeManajerView
import com.dev.properti.ui.view.manajerproperti.InsertManajerView
import com.dev.properti.ui.view.manajerproperti.UpdateManajerView
import com.dev.properti.ui.view.pemilik.DestinasiDetailPemilik
import com.dev.properti.ui.view.pemilik.DestinasiHomePemilik
import com.dev.properti.ui.view.pemilik.DestinasiInsertPemilik
import com.dev.properti.ui.view.pemilik.DestinasiUpdatePemilik
import com.dev.properti.ui.view.pemilik.DetailPemilikView
import com.dev.properti.ui.view.pemilik.HomePemilikView
import com.dev.properti.ui.view.pemilik.InsertPemilikView
import com.dev.properti.ui.view.pemilik.UpdatePemilikView
import com.dev.properti.ui.view.properti.DestinasiDetailProperti
import com.dev.properti.ui.view.properti.DestinasiHomeProperti
import com.dev.properti.ui.view.properti.DestinasiInsertProperti
import com.dev.properti.ui.view.properti.DestinasiUpdateProperti
import com.dev.properti.ui.view.properti.DetailPropertiView
import com.dev.properti.ui.view.properti.HomePropertiView
import com.dev.properti.ui.view.properti.InsertPropertiView
import com.dev.properti.ui.view.properti.UpdatePropertiView

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeApp.route,
        modifier = Modifier
    ) {
        // Home App
        composable(
            route = DestinasiHomeApp.route
        ) {
            HomeApp(
                modifier = Modifier,
                onPemilikClick = {
                    navController.navigate(DestinasiHomePemilik.route)
                },
                onManajerClick = {
                    navController.navigate(DestinasiHomeManajer.route)
                },
                onJenisClick = {
                    navController.navigate(DestinasiHomeJenis.route)
                },
                onPropertiClick = {
                    navController.navigate(DestinasiHomeProperti.route) // Sesuaikan dengan route properti jika ada
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Pemilik
        composable(DestinasiHomePemilik.route) {
            HomePemilikView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPemilik.route) },
                onDetailClick = { id_pemilik ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$id_pemilik")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(DestinasiInsertPemilik.route) {
            InsertPemilikView(
                navigateBack = {
                    navController.navigate(DestinasiHomePemilik.route) {
                        popUpTo(DestinasiHomePemilik.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            DestinasiDetailPemilik.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPemilik.IDPMK) { type = NavType.IntType }
            )
        ) {
            val id_pemilik = it.arguments?.getInt(DestinasiDetailPemilik.IDPMK)
            id_pemilik?.let { id ->
                DetailPemilikView(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdatePemilik.route}/$id")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiHomePemilik.route) {
                            popUpTo(DestinasiHomePemilik.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiUpdatePemilik.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePemilik.IDPMK) { type = NavType.IntType }
            )
        ) {
            val id_pemilik = it.arguments?.getInt(DestinasiUpdatePemilik.IDPMK)
            id_pemilik?.let {
                UpdatePemilikView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // Manajer
        composable(DestinasiHomeManajer.route) {
            HomeManajerView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertManajer.route) },
                onDetailClick = { id_manajer ->
                    navController.navigate("${DestinasiDetailManajer.route}/$id_manajer")
                    println(id_manajer)
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) { inclusive = true }
                    }
                }
            )
        }


        composable(DestinasiInsertManajer.route) {
            InsertManajerView(
                navigateBack = {
                    navController.navigate(DestinasiHomeManajer.route) {
                        popUpTo(DestinasiHomeManajer.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            DestinasiDetailManajer.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailManajer.IDMJR) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_manajer = backStackEntry.arguments?.getInt(DestinasiDetailManajer.IDMJR)
            // ...

        id_manajer?.let { id_manajer ->
                DetailManajerView(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdateManajer.route}/$id_manajer")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiHomeManajer.route) {
                            popUpTo(DestinasiHomeManajer.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiUpdateManajer.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateManajer.IDMJR) { type = NavType.IntType }
            )
        ) {
            val id_manajer = it.arguments?.getInt(DestinasiUpdateManajer.IDMJR)
            id_manajer?.let {
                UpdateManajerView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // Jenis Properti
        composable(DestinasiHomeJenis.route) {
            HomeJenisView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertJenis.route) },
                onDetailClick = { id_jenis ->
                    navController.navigate("${DestinasiDetailJenis.route}/$id_jenis")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(DestinasiInsertJenis.route) {
            InsertJenisView(
                navigateBack = {
                    navController.navigate(DestinasiHomeJenis.route) {
                        popUpTo(DestinasiHomeJenis.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            DestinasiDetailJenis.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailJenis.IDJNS) { type = NavType.IntType }
            )
        ) {
            val id_jenis = it.arguments?.getInt(DestinasiDetailJenis.IDJNS)
            id_jenis?.let { id_jenis ->
                DetailJenisView(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdateJenis.route}/$id_jenis")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiHomeJenis.route) {
                            popUpTo(DestinasiHomeJenis.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiUpdateJenis.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJenis.IDJNS) { type = NavType.IntType }
            )
        ) {
            val id_jenis = it.arguments?.getInt(DestinasiUpdateJenis.IDJNS)
            id_jenis?.let {
                UpdateJenisView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        composable(DestinasiHomeProperti.route) {
            HomePropertiView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertProperti.route) },
                onDetailClick = {id_properti ->
                    navController.navigate("${DestinasiDetailProperti.route}/$id_properti")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route)
                }
            )
        }

        composable(DestinasiInsertProperti.route) {
            InsertPropertiView(navigateBack = {
                navController.navigate(DestinasiHomeProperti.route) {
                    popUpTo(DestinasiHomeProperti.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetailProperti.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailProperti.IDPPR) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_properti = it.arguments?.getString(DestinasiDetailProperti.IDPPR)
            id_properti?.let { id_properti ->
                DetailPropertiView(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateProperti.route}/$id_properti")
                    },
                    id_properti = id_properti.toInt(),
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            route = DestinasiUpdateProperti.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateProperti.IDPPR) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id_properti = backStackEntry.arguments?.getString(DestinasiUpdateProperti.IDPPR)
            id_properti?.let {
                UpdatePropertiView(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeProperti.route) {
                            popUpTo(DestinasiHomeProperti.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

