package com.veeransh.aifashion.enterprise.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.veeransh.aifashion.enterprise.data.local.entity.ProductEntity
import com.veeransh.aifashion.enterprise.ui.viewmodel.SuperAdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperAdminDashboard(
    navController: NavController,
    viewModel: SuperAdminViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Access", "COD", "Display", "Coupon", "Pincode", "System")
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Super Admin V3.2", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D5C36),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { 
                    try {
                        navController.navigate("ai_drape_studio") 
                    } catch(e: Exception) {
                        android.util.Log.e("Drape", "Navigation to AI Drape Studio failed", e)
                    }
                },
                containerColor = Color(0xFF0D5C36),
                contentColor = Color.White,
                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null) },
                text = { Text("AI Drape Studio") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 8.dp,
                containerColor = Color.White,
                contentColor = Color(0xFF0D5C36),
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { 
                            Text(
                                text = title, 
                                fontSize = 12.sp, 
                                maxLines = 1, 
                                overflow = TextOverflow.Ellipsis 
                            ) 
                        }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F1E8))) {
                when (selectedTabIndex) {
                    0 -> AccessTab(viewModel, snackbarHostState)
                    1 -> CODTab(viewModel, snackbarHostState)
                    2 -> DisplayTab(viewModel)
                    3 -> CouponTab(snackbarHostState)
                    4 -> PincodeTab(snackbarHostState)
                    5 -> SystemTab(viewModel, snackbarHostState)
                }
            }
        }
    }
}

@Composable
fun AccessTab(viewModel: SuperAdminViewModel, snackbarHostState: SnackbarHostState) {
    val adminPin by viewModel.adminPin.collectAsState()
    var pinText by remember(adminPin) { mutableStateOf(adminPin) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Security PIN", fontWeight = FontWeight.Bold, color = Color(0xFF0D5C36))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = pinText,
                    onValueChange = { pinText = it },
                    label = { Text("Admin Access PIN") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Button(
                    onClick = { 
                        viewModel.updateAdminPin(pinText)
                        scope.launch { snackbarHostState.showSnackbar("PIN Updated") }
                    },
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D5C36))
                ) {
                    Text("Save PIN")
                }
            }
        }
        
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("User Role", fontWeight = FontWeight.Bold, color = Color(0xFF0D5C36))
                var selectedRole by remember { mutableStateOf("Super Admin") }
                listOf("Super Admin", "Dealer", "Staff").forEach { role ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedRole == role, onClick = { selectedRole = role })
                        Text(role)
                    }
                }
            }
        }
    }
}

@Composable
fun CODTab(viewModel: SuperAdminViewModel, snackbarHostState: SnackbarHostState) {
    val enableCod by viewModel.enableCod.collectAsState()
    val minCod by viewModel.minCod.collectAsState()
    val maxCod by viewModel.maxCod.collectAsState()
    val codCharge by viewModel.codCharge.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text("Enable COD Service", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Switch(checked = enableCod, onCheckedChange = { viewModel.updateCodSettings(it, minCod, maxCod, codCharge) })
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = minCod.toString(),
                    onValueChange = { val v = it.toIntOrNull() ?: 0; viewModel.updateCodSettings(enableCod, v, maxCod, codCharge) },
                    label = { Text("Minimum Amount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = maxCod.toString(),
                    onValueChange = { val v = it.toIntOrNull() ?: 0; viewModel.updateCodSettings(enableCod, minCod, v, codCharge) },
                    label = { Text("Maximum Amount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = codCharge.toString(),
                    onValueChange = { val v = it.toIntOrNull() ?: 0; viewModel.updateCodSettings(enableCod, minCod, maxCod, v) },
                    label = { Text("COD Charges (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

@Composable
fun DisplayTab(viewModel: SuperAdminViewModel) {
    val products by viewModel.products.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(products) { product ->
            Card(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                ProductInventoryItem(
                    product = product,
                    onAdd = { viewModel.updateStock(product, 1) },
                    onRemove = { viewModel.updateStock(product, -1) }
                )
            }
        }
    }
}

@Composable
fun ProductInventoryItem(product: ProductEntity, onAdd: () -> Unit, onRemove: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("SKU: ${product.sku}", fontSize = 12.sp, color = Color.Gray)
            Text("Stock: ${product.stock}", color = if (product.stock < 5) Color.Red else Color(0xFF0D5C36), fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onRemove) { Icon(Icons.Default.Remove, contentDescription = null) }
            Text("${product.stock}", modifier = Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Black)
            IconButton(onClick = onAdd) { Icon(Icons.Default.Add, contentDescription = null) }
        }
    }
}

@Composable
fun CouponTab(snackbarHostState: SnackbarHostState) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Product Coupons", fontWeight = FontWeight.Bold, color = Color(0xFF0D5C36))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Apply Flat 5% Off", modifier = Modifier.weight(1f))
                    Switch(checked = true, onCheckedChange = {})
                }
                Text("Sample: ₹1000 -> ₹950", fontSize = 12.sp, color = Color.Gray)
            }
        }
        
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Cart Offers", fontWeight = FontWeight.Bold, color = Color(0xFF0D5C36))
                OutlinedTextField(value = "999", onValueChange = {}, label = { Text("Min Cart Value") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D5C36))) {
                    Text("Update Offer")
                }
            }
        }
    }
}

@Composable
fun PincodeTab(snackbarHostState: SnackbarHostState) {
    var newPincode by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = newPincode,
            onValueChange = { if(it.length <= 6) newPincode = it },
            label = { Text("Add Serviceable Pincode") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = { newPincode = "" },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D5C36))
        ) {
            Text("Add to List")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Serviceable Areas", fontWeight = FontWeight.Bold)
        LazyColumn {
            items(listOf("302001", "302020", "110001")) { pc ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(pc, modifier = Modifier.weight(1f))
                    Text("COD Available", fontSize = 12.sp, color = Color.Gray)
                    IconButton(onClick = {}) { Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red) }
                }
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
fun SystemTab(viewModel: SuperAdminViewModel, snackbarHostState: SnackbarHostState) {
    val aiEnabled by viewModel.bananaAiEnabled.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("System Config", fontWeight = FontWeight.Bold, color = Color(0xFF0D5C36))
                SystemSwitch("Enable Banana AI", aiEnabled) { viewModel.updateBananaAiEnabled(it) }
                SystemSwitch("Well Packed Badge", true) {}
                SystemSwitch("OTP Login", true) {}
                SystemSwitch("No Return Policy", false) {}
            }
        }
        
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Payment Gateway", fontWeight = FontWeight.Bold, color = Color(0xFF0D5C36))
                OutlinedTextField(value = "rzp_live_xxxxxxxx", onValueChange = {}, label = { Text("Razorpay Key ID") }, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun SystemSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
