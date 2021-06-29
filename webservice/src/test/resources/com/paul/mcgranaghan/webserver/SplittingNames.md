# Splitting Names

To help personalise our mailshots we want to have the first name and last name of the customer. Unfortunately the
customer data that we are supplied only contains full names.

The system therefore attempts to break a supplied full name into its constituents by splitting around whitespace.

### [Example 1](- "basic c:status=Ignored")

The full name [Jane Smith](- "#name") is [broken](- "#result = split(#name)") into first
name [Jane](- "?=#result.firstName") and last name [Smith](- "?=#result.lastName").

### [Example 2](- "basic2 c:status=Ignored")

The full name [John Smith](- "#name") is [broken](- "#result = split(#name)") into first
name [John](- "?=#result.firstName") and last name [Smith](- "?=#result.lastName").




