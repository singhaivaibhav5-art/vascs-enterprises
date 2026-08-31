# VEERANSH AI FASHION ERP ULTIMA Implementation Plan

Rebranding the VASCS Enterprise app to **VEERANSH AI FASHION** and implementing the ERP Ultima Blueprint.

## User Review Required

> [!IMPORTANT]
> - **Logo Files Missing:** The requested logo files (`brand_name.webp`, `veeransh_close_gap_transparent.png`, `Crystal_V_On_Circuit`) were not found in the project's `res/drawable` folder. I will proceed with the code structure, but you will need to add these actual image files to the project.
> - **Firebase Credentials:** The Firebase configuration provided in the chat will be used to initialize Firebase. I will create a `google-services.json` skeleton or use `FirebaseOptions` in code for initialization.
> - **Package Migration:** I will update the `applicationId` and branding strings. Source code will remain in `com.example.vascs` for backward compatibility and to avoid breaking 200+ DAO references, while exposing the new branding in UI.

## Proposed Changes

### 1. Branding & Theming
Update the visual identity of the app.

#### [MODIFY] [colors.xml](file:///C:/Users/singh/Downloads/vascs/app/src/main/res/values/colors.xml)
- Add Emerald (#0A5C36), Royal Gold (#C9A86A), Rose Gold (#D4A0A0), Cream (#FEF9F0).
#### [MODIFY] [strings.xml](file:///C:/Users/singh/Downloads/vascs/app/src/main/res/values/strings.xml)
- Update `app_name` to "VEERANSH AI FASHION".
- Add brand slogans and localization strings.

---

### 2. OS 7: Auth & Security
Implement secure authentication and admin controls.

#### [NEW] [AuthViewModel.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/viewmodel/AuthViewModel.kt)
- OTP Signup/Login logic.
- 2FA (Email/Authenticator) support.
- Lost Mobile Recovery flow.
#### [NEW] [LoginScreen.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/screens/LoginScreen.kt)
- Two-tab login (OTP/Password).
- Signup with mandatory OTP and recovery email.
#### [NEW] [AdminSecurityScreen.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/screens/AdminSecurityScreen.kt)
- User search, suspend (timed), and block functionality.
- Audit logs view.

---

### 3. OS 3 & OS 5: Admin & Coupons
Customizable modules and 2-tier coupon system.

#### [NEW] [AdminPanelScreen.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/screens/AdminPanelScreen.kt)
- Left sidebar navigation for Categories, Banners, Products, etc.
- Drag-and-drop reordering and toggle visibility.
#### [NEW] [CouponViewModel.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/viewmodel/CouponViewModel.kt)
- Logic for product-level (immediate) and cart-level (checkout) coupons.

---

### 4. OS 4: Style Partner (Dealer)
B2B innovation and referral tracking.

#### [MODIFY] [DealerNetworkViewModel.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/viewmodel/DealerNetworkViewModel.kt)
- Add Style Partner levels (Silver, Gold, Platinum, Diamond).
- Referral logic (commission on shared product only).
- eWallet with 7-day blur for pending balance.
#### [NEW] [StylePartnerDashboard.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/screens/StylePartnerDashboard.kt)
- Wallet view, referral links, and bank detail management.

---

### 5. OS 2: AI Fashion Display
Product detail creation from AI images.

#### [MODIFY] [AiArchiveViewModel.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/viewmodel/AiArchiveViewModel.kt)
- Add "Move to Display" logic.
#### [NEW] [ProductDetailCreationScreen.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/screens/ProductDetailCreationScreen.kt)
- 2-column layout for product identity (SKU, QR, Barcode) and metadata.
- Smart auto-resizing based on display locations.

---

### 6. OS 6: Inventory & Logistics
Order flow and policies.

#### [MODIFY] [OrderDispatchViewModel.kt](file:///C:/Users/singh/Downloads/vascs/app/src/main/java/com/example/vascs/ui/viewmodel/OrderDispatchViewModel.kt)
- New statuses: Ready to Move, Veeransh QC Hub, Well Packed, Shipped, Delivered.
- Mandatory "No Return/Exchange" clause acceptance at checkout.

## Verification Plan

### Automated Tests
- `gradlew :app:assembleDebug` to ensure no build regressions.
- Unit tests for Coupon logic and eWallet commission calculation.

### Manual Verification
- Verify branding colors and strings in the UI.
- Test the Login/OTP flow in a mock environment.
- Simulate a Style Partner referral purchase and check eWallet "blur" state.
