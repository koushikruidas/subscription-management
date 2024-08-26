# Subscription Management System

## Overview

This project is a subscription management system designed to handle complex subscription models, phase transitions, usage tracking, tiered pricing, and billing. The system is inspired by the Kill Bill subscription platform and provides a flexible entity design to accommodate various subscription scenarios.

## Entity Design

### Key Entities and Relationships

1. **Billing**
2. **Catalog**
3. **Phase**
4. **Plan**
5. **PriceList**
6. **Product**
7. **Subscription**
8. **Tier**
9. **Usage**

### Entity Relationships

- **Billing** is associated with **Subscription**
- **Catalog** contains multiple **Products**
- **Phase** is part of a **Plan** and has multiple **Subscriptions** and **Usages**
- **Plan** belongs to a **Product** and has multiple **Phases**, **PriceLists**, and **Subscriptions**
- **PriceList** is associated with multiple **Plans**
- **Product** belongs to a **Catalog** and has multiple **Plans**
- **Subscription** is linked to a **Plan** and **Phase**, and generates multiple **Billings**
- **Tier** is associated with **Usage**
- **Usage** is part of a **Phase** and has multiple **Tiers**

## Example Scenarios

### Example 1: Subscription to a Product Plan with Phase Transitions and Billing

#### Scenario

- A customer subscribes to a premium product plan which has a free trial phase followed by a paid phase.
- The plan includes usage tracking for a specific feature with tiered pricing.
- Billing occurs monthly, and the system must transition the subscription from the trial phase to the paid phase automatically.

#### Entities and Relationships

1. **Product and Catalog**
    - **Catalog**: `Version 1.0` containing various products.
    - **Product**: `Premium Product` available in this catalog version.

2. **Plan and Product**
    - **Plan**: `Premium Plan` associated with the `Premium Product`.
    - **Recurring Billing Period**: `Monthly`.

3. **Phases of the Plan**
    - **Trial Phase**:
        - Type: `Trial`
        - Duration: `1 month`
        - Recurring Price: `0.00`
    - **Paid Phase**:
        - Type: `Paid`
        - Duration: `Indefinite`
        - Recurring Price: `$29.99`

4. **Subscription and Phases**
    - **Subscription**:
        - Start Date: `2023-01-01`
        - Initially associated with the `Trial Phase`.
        - Automatically transitions to the `Paid Phase` after the trial period ends.

5. **Usage and Tier**
    - **Usage**: `Feature Usage` tracked monthly.
    - **Tier**:
        - Lower Bound: `0`
        - Upper Bound: `100`
        - Fixed Price: `$0.00`
        - Recurring Price: `$0.10` per unit.

6. **Billing**
    - Generates monthly invoices based on the current phase's pricing and any usage tiers.

#### Implementation Details

1. **Creating Catalog and Product**

```java
Catalog catalog = new Catalog();
catalog.setVersion("1.0");

Product product = new Product();
product.setName("Premium Product");
product.setType("Software");
product.setCatalogVersion("1.0");
product.setAvailable(true);

catalog.setProducts(Arrays.asList(product));
```
2. **Defining Plan and Phases**

```java
Plan premiumPlan = new Plan();
premiumPlan.setName("Premium Plan");
premiumPlan.setRecurringBillingPeriod("Monthly");
premiumPlan.setProduct(product);

Phase trialPhase = new Phase();
trialPhase.setType("Trial");
trialPhase.setDuration(1);
trialPhase.setRecurringBillingPeriod("Monthly");
trialPhase.setFixedPrice(BigDecimal.ZERO);
trialPhase.setRecurringPrice(BigDecimal.ZERO);
trialPhase.setPlan(premiumPlan);

Phase paidPhase = new Phase();
paidPhase.setType("Paid");
paidPhase.setDuration(-1);  // Indefinite duration
paidPhase.setRecurringBillingPeriod("Monthly");
paidPhase.setFixedPrice(new BigDecimal("29.99"));
paidPhase.setRecurringPrice(new BigDecimal("29.99"));
paidPhase.setPlan(premiumPlan);

premiumPlan.setPhases(Arrays.asList(trialPhase, paidPhase));

```
3. **Creating Subscription**

```java
Subscription subscription = new Subscription();
subscription.setStartDate(LocalDate.of(2023, 1, 1));
subscription.setBillingStartDate(LocalDate.of(2023, 1, 1));
subscription.setNextBillingDate(LocalDate.of(2023, 2, 1));
subscription.setPlan(premiumPlan);
subscription.setCurrentPhase(trialPhase);

```
4. **Tracking Usage and Tiered Pricing**

```java
Usage featureUsage = new Usage();
featureUsage.setName("Feature Usage");
featureUsage.setType("Monthly");
featureUsage.setBillingPeriod("Monthly");
featureUsage.setPhase(trialPhase);  // Initial phase usage

Tier usageTier = new Tier();
usageTier.setLowerBound(0);
usageTier.setUpperBound(100);
usageTier.setFixedPrice(BigDecimal.ZERO);
usageTier.setRecurringPrice(new BigDecimal("0.10"));
usageTier.setUsage(featureUsage);

featureUsage.setTiers(Arrays.asList(usageTier));

```

5. **Generate Billing**

```java
Billing firstMonthBilling = new Billing();
firstMonthBilling.setBillingDate(LocalDate.of(2023, 2, 1));
firstMonthBilling.setAmount(BigDecimal.ZERO);  // Free trial
firstMonthBilling.setSubscription(subscription);

// Add billing to subscription
subscription.setBillings(Arrays.asList(firstMonthBilling));

```

6. **Phase Transition**

- The system will check the subscription's phase and automatically transition to the paid phase after the trial period ends.
```java
if (subscription.getNextBillingDate().equals(LocalDate.of(2023, 2, 1))) {
    subscription.setCurrentPhase(paidPhase);
    subscription.setNextBillingDate(LocalDate.of(2023, 3, 1));
}

// Generate billing for the new phase
Billing secondMonthBilling = new Billing();
secondMonthBilling.setBillingDate(LocalDate.of(2023, 3, 1));
secondMonthBilling.setAmount(new BigDecimal("29.99"));  // Paid phase pricing
secondMonthBilling.setSubscription(subscription);

subscription.getBillings().add(secondMonthBilling);

```

### Example 2: Subscription with Multiple Price Lists

#### Scenario

- A customer subscribes to a basic product plan which is listed under multiple price lists.
- The plan offers different pricing strategies for different customer segments (e.g., retail and enterprise).

#### Entities and Relationships

1. **Product and Catalog**
    - **Catalog**: `Version 2.0` containing various products.
    - **Product**: `Basic Product` available in this catalog version.

2. **Plan and Product**
    - **Plan**: `Basic Plan` associated with the `Basic Product`.
    - **Recurring Billing Period**: `Monthly`.

3. **Price Lists and Plans**
    - **Price Lists**:
        - `Retail Price List`
        - `Enterprise Price List`
    - **Plan**: `Basic Plan` listed under both price lists with different pricing strategies.

#### Implementation Details

1. **Creating Catalog and Product**

```java
Catalog catalog = new Catalog();
catalog.setVersion("2.0");

Product product = new Product();
product.setName("Basic Product");
product.setType("Service");
product.setCatalogVersion("2.0");
product.setAvailable(true);

catalog.setProducts(Arrays.asList(product));
```

2. **Defining Plan and Price Lists**

```java
Plan basicPlan = new Plan();
basicPlan.setName("Basic Plan");
basicPlan.setRecurringBillingPeriod("Monthly");
basicPlan.setProduct(product);

PriceList retailPriceList = new PriceList();
retailPriceList.setName("Retail Price List");
retailPriceList.setDefault(true);

PriceList enterprisePriceList = new PriceList();
enterprisePriceList.setName("Enterprise Price List");
enterprisePriceList.setDefault(false);

basicPlan.setPriceLists(Arrays.asList(retailPriceList, enterprisePriceList));

```

3. **Creating Subscription**
```java
Subscription subscription = new Subscription();
subscription.setStartDate(LocalDate.of(2023, 1, 1));
subscription.setBillingStartDate(LocalDate.of(2023, 1, 1));
subscription.setNextBillingDate(LocalDate.of(2023, 2, 1));
subscription.setPlan(basicPlan);

```

4. **Generate Billing**
```java
Billing billing = new Billing();
billing.setBillingDate(LocalDate.of(2023, 2, 1));
billing.setAmount(new BigDecimal("19.99"));  // Assuming Retail Price List amount
billing.setSubscription(subscription);

// Add billing to subscription
subscription.setBillings(Arrays.asList(billing));

```