

*Disclaimer*: I worked alongside Morten Stulen, so our assignments are inspired by each other.  
Also, please evaluate assignment 1 again: https://github.com/krissrex/ntnu-tdt4250-study-emf .
Blackboard did not allow resubmission after deadline, and feedback came after deadline...

## Running

The main bundle is `no.ntnu.tdt4250.oving2.server`.
Run that one.

The project was made with Java 11, but no 11 specific APIs are used.
Make sure your distribution has Nashorn (Java's javascript engine). It is scheduled for removal in newer JDKs.

## Paths:

* `http://localhost:8080/convert?from=mph&to=kmh&value=50`
* `http://localhost:8080/convert/list`
* `http://localhost:8080/convert/list?from=kg`

## Gogo commands

```
conversion:addConversion expression:String
conversion:convert value:Double from:String to:String
conversion:list
conversion:removeConversion from:String to:String
```

### Adding a custom converter with gogo

`addConversion "G = 50 * H + 1"`  
`http://localhost:8080/convert?from=H&to=G&value=1`

### Removing a custom converter

`removeConversion "H" "G"`

### Listing all converters

`conversion:list`  

```
Listing all conversions
Conversion [from=g, to=kg]
Conversion [from=kg, to=g]
Conversion [from=kg, to=lb]
Conversion [from=mph, to=kmh]
```

### Converting

`addConversion "F = C * 1.8 + 32"`  
`convert 20 F C`

```
Converting 20.0 C to F
68.0
```