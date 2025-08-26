// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "DataCrypto",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "DataCrypto",
            targets: ["DataCrypto"]),
    ],
    dependencies: [
        .package(path: "../Domain"),
        .package(path: "../CoreCommon")
    ],
    targets: [
        .target(
            name: "DataCrypto",
            dependencies: ["Domain", "CoreCommon"]),
        .testTarget(
            name: "DataCryptoTests",
            dependencies: ["DataCrypto", "Domain"]),
    ]
)
