# Core Geography Module

## Overview
Small but essential, this module manages universal geographic master data. It provides a standardized way to handle locations across all tenants, ensuring consistent data entry for patient addresses and clinic locations.

## Key Responsibilities
- **Geo-Catalog**: Manages the hierarchy of Countries, Regions/States, and Cities.
- **Standardized Search**: Provides optimized lookup services for location-based data.

## Table Prefix
Tables in this module use the **`GEO_`** prefix.

## Usage
Include this module whenever geographic selection or address management is required in a business domain.
```xml
<dependency>
    <groupId>com.amachi.app.core</groupId>
    <artifactId>core-geography</artifactId>
</dependency>
```
