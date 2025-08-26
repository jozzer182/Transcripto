// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Domain",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "Domain",
            targets: ["Domain"]),
    ],
    dependencies: [
        .package(path: "../CoreCommon")
    ],
    targets: [
        .target(
            name: "Domain",
            dependencies: ["CoreCommon"]),
        .testTarget(
            name: "DomainTests",
            dependencies: ["Domain"]),
    ]
)
