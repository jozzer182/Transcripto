// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "TestingSupport",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(name: "TestingSupport", targets: ["TestingSupport"])
    ],
    dependencies: [
        .package(name: "Domain", path: "../Domain"),
        .package(name: "DataCrypto", path: "../DataCrypto"),
        .package(name: "CoreCommon", path: "../CoreCommon")
    ],
    targets: [
        .target(name: "TestingSupport", dependencies: ["Domain", "DataCrypto", "CoreCommon"]) // local deps via relative path
    ]
)
