# VEERANSH AI FASHION - Veeransh AI Saree Catalogue Studio (Android Application)

Native Android application built with Kotlin, Jetpack Compose, Material Design 3, and Room SQLite Database.

## Features & Functional Scope

1. **Saree Master Catalogue**:
   - Manage saree items with Name, SKU, Barcode, Category (Banarasi, Kanjeevaram, Silk, Chiffon, Georgette, Organza, Cotton, Paithani), Fabric, Colour, Size, HSN Code, GST (%), Purchase Price, Wholesale Price, Retail Price, MRP, and Stock levels.
   - Filter by Category, Colour, Brand, and Stock Availability with real-time reactive search.

2. **Product Batches Management**:
   - Organize sarees into production lot batches (e.g. "Festive Banarasi Royal Collection").
   - Track total batch items, completed items, status badges (ACTIVE, DRAFT, COMPLETED), and batch level detail views.

3. **Pricing & Margin Studio**:
   - Interactive profit margin and pricing calculator.
   - Calculate retail selling price, wholesale prices, GST tax breakdown, and suggested MRP tags based on discount percentages.

4. **Barcode Tag & Label Print Studio**:
   - Generate high-resolution Code 128 barcodes and QR codes for sarees using ZXing.
   - Customized Saree Tag previews with store branding, care instructions, price tags, HSN, and GST information.

5. **Local Data Persistence**:
   - Powered by Room Database (`VascsDatabase`) with reactive Kotlin `Flow`s and `StateFlow`.
